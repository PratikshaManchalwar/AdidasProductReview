# ProductReview
Goal of this android application is to get the products overview and it's details.
It also maintains the product review system.

#CONGIFURATION
git clone [url]

#API SETUP

#clone this project to get api code
git clone https://bitbucket.org/adichallenge/product-reviews-docker-composer.git

#Run api locally
docker-compose up

#API DETAILS
#Product API
http://localhost:3001/api-docs/swagger/
#Review API
http://localhost:3002/api/ 

#NOTE
(replace localhost with your system ip, if application doesn't works in RetrofitBuilder) 
    private const val BASE_URL_PRODUCT = "http://locahost:3001/"
    private const val BASE_URL_REVIEW = "http://locahost:3002/"
