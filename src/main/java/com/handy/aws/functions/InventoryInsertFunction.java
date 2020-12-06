package com.handy.aws.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import java.util.List;

public class InventoryInsertFunction extends InventoryS3Client implements RequestHandler<HttpRequest, HttpProductResponse> {
    @Override
    public HttpProductResponse handleRequest(HttpRequest request, Context context) {
        context.getLogger().log("Input: " + request);
        String body = request.getBody();
        Gson gson = new Gson();
        Product productToAdd = gson.fromJson(body, Product.class);
        List<Product> productList = getAllProductsList();
        productList.add(productToAdd);

        if(updateAllProducts(productList)) {
            return new HttpProductResponse();
        }

        HttpProductResponse response = new HttpProductResponse();
        response.setStatusCode("500");
        return response;
    }
}
