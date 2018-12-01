package com.homesorderconsumer.api;

import com.homesorderconsumer.MyApp;
import com.homesorderconsumer.product.FieldsEnum;
import com.homesorderconsumer.product.SortByEnum;
import com.homesorderconsumer.product.model.Option;
import com.homesorderconsumer.user.cart.model.AddToCart;
import com.homesorderconsumer.user.cart.model.AddToCartDeliverySlots;
import com.homesorderconsumer.user.cart.model.AddToCartOption;
import com.homesorderconsumer.product.model.ProductDataCollection;
import com.homesorderconsumer.product.model.ProductDetail;
import com.homesorderconsumer.product.model.ProductRequest;
import com.homesorderconsumer.product.model.ProductResponse;
import com.homesorderconsumer.product.model.Products;
import com.homesorderconsumer.product.model.SearchCriteria;
import com.homesorderconsumer.product.model.SelectedDeliverySlot;
import com.homesorderconsumer.product.model.Sortby;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.cart.model.CartItem;
import com.homesorderconsumer.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 27/03/18.
 */

public class APIUtil {
    private static final APIUtil ourInstance = new APIUtil();

    public static APIUtil getInstance() {
        return ourInstance;
    }

    private APIUtil() {
    }

    public String getUserToken(){
        if (MySession.getInstance(MyApp.getContext()).isLogin()) {//Logged user
            return MySession.getInstance(MyApp.getContext()).getUser().getToken();
        }else { //Guest User
            return "";
        }
    }

    public AddToCart addToCart(ProductDetail productDetail, ProductDataCollection
            productDataCollection, SelectedDeliverySlot selectedDeliverySlot){
        AddToCart addToCart=new AddToCart();
        addToCart.setProductID(productDetail.getProductID());
        addToCart.setQty(productDataCollection.getQuantity());
        AddToCartOption addToCartOption=new AddToCartOption();
        if (productDataCollection.getColorValue().trim().length()!=0) {
            addToCartOption.setColor(productDataCollection.getColorValue());
        }
        if (productDataCollection.getSizeValue().trim().length()!=0) {
            addToCartOption.setSize(productDataCollection.getSizeValue());
        }

        if (productDataCollection.getWeightValue().trim().length()!=0) {
            addToCartOption.setWeight(productDataCollection.getWeightValue());
        }

        addToCart.setOption(addToCartOption);
        AddToCartDeliverySlots addToCartDeliverySlots =new AddToCartDeliverySlots();
        addToCartDeliverySlots.setDate(selectedDeliverySlot.getDate());
        addToCartDeliverySlots.setSlots(selectedDeliverySlot.getSelectedsSlot());
        addToCart.setDeliverySlots(addToCartDeliverySlots);
        return addToCart;
    }

    public AddToCart updateCart(CartItem cartItem, String qty){
        AddToCart addToCart=new AddToCart();
        addToCart.setProductID(cartItem.getProduct().getProductID());
        addToCart.setQty(qty);
        AddToCartOption addToCartOption=new AddToCartOption();
        Option option=cartItem.getProduct().getOption();
        if (option!=null&&option.getColor().size()!=0){
            addToCartOption.setColor(option.getColor().get(0).getValue());
        }
        if (option!=null&&option.getSize().size()!=0){
            addToCartOption.setSize(option.getSize().get(0).getValue());
        }
        if (option!=null&&option.getWeight().size()!=0){
            addToCartOption.setWeight(option.getWeight().get(0).getValue());
        }
        addToCart.setOption(addToCartOption);
        return addToCart;
    }

    public List<Products> parseFeaturedProduct(ProductResponse productResponse){
        List<Products> products=new ArrayList<>();
        products=productResponse.getProducts();
        if (productResponse.getFeaturedProducts()!=null&&productResponse.getFeaturedProducts()
                .size()!=0){
            for (int i=0;i<productResponse.getFeaturedProducts().size();i++){
                Products tempProducts=productResponse.getFeaturedProducts().get(i);
                tempProducts.setFeaturedProduct(true);
                products.add(i,tempProducts);
            }
        }
        return products;
    }

    public ProductRequest productBasedOnCategory(String categoryID, int pageNo){
        ProductRequest productRequest=new ProductRequest();
        productRequest.setCategoryID(categoryID);
        productRequest.setPage(String.valueOf(pageNo));
        List<SearchCriteria> criteria=new ArrayList<>();
        criteria.add(selectedArea());
        productRequest.setSearchCriteria(criteria);

        List<Sortby> sortbyList=new ArrayList<>();
        sortbyList.add(mostRecent());
        productRequest.setSortby(sortbyList);
        return productRequest;
    }

    public ProductRequest productSearch(String productName, int pageNo){
        ProductRequest productRequest=new ProductRequest();
        productRequest.setCategoryID("");
        productRequest.setPage(String.valueOf(pageNo));

        List<SearchCriteria> criteria=new ArrayList<>();
        criteria.add(selectedArea());
        criteria.add(new SearchCriteria(FieldsEnum.NAME.getValue(),productName));
        criteria.add(new SearchCriteria(FieldsEnum.BRANDS.getValue(),productName));
        productRequest.setSearchCriteria(criteria);

        List<Sortby> sortbyList=new ArrayList<>();
        sortbyList.add(mostRecent());
        productRequest.setSortby(sortbyList);
        return productRequest;
    }

    public ProductRequest productMostRecent(String categoryID, int pageNo){
        ProductRequest productRequest=new ProductRequest();
        productRequest.setCategoryID(categoryID);
        productRequest.setPage(String.valueOf(pageNo));
        List<SearchCriteria> criteria=new ArrayList<>();
        criteria.add(selectedArea());
        productRequest.setSearchCriteria(criteria);

        List<Sortby> sortbyList=new ArrayList<>();
        sortbyList.add(mostRecent());
        productRequest.setSortby(sortbyList);
        return productRequest;
    }

    public ProductRequest productPriceLowToHigh(String categoryID, int pageNo){

        ProductRequest productRequest=new ProductRequest();

        productRequest.setCategoryID(categoryID);
        productRequest.setPage(String.valueOf(pageNo));

        List<SearchCriteria> criteria=new ArrayList<>();
        criteria.add(selectedArea());
        productRequest.setSearchCriteria(criteria);

        List<Sortby> sortbyList=new ArrayList<>();
        sortbyList.add(priceLowToHigh());
        productRequest.setSortby(sortbyList);

        return productRequest;
    }

    public ProductRequest productPriceHighToLow(String categoryID, int pageNo){

        ProductRequest productRequest=new ProductRequest();

        productRequest.setCategoryID(categoryID);
        productRequest.setPage(String.valueOf(pageNo));

        List<SearchCriteria> criteria=new ArrayList<>();
        criteria.add(selectedArea());
        productRequest.setSearchCriteria(criteria);

        List<Sortby> sortbyList=new ArrayList<>();
        sortbyList.add(priceHighToLow());
        productRequest.setSortby(sortbyList);

        return productRequest;
    }

    private Sortby mostRecent(){
        return new Sortby(FieldsEnum.MOST_RECENT.getValue(), SortByEnum.DESC.getValue());
    }

    private Sortby priceLowToHigh(){
        return new Sortby(FieldsEnum.PRICE.getValue(), SortByEnum.ASC.getValue());
    }

    private Sortby priceHighToLow(){
        return new Sortby(FieldsEnum.PRICE.getValue(), SortByEnum.DESC.getValue());
    }

    private SearchCriteria selectedArea(){
        return new SearchCriteria(FieldsEnum.AREA.getValue(),StringUtil.selectedAreaValue());
    }

}
