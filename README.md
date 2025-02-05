# Report-Portal-TAF
Test automation framework for testing Report Portal

start

colima start --cpu 4 --memory 8 --disk 60

docker run -d --name selenoid-ui \
-p 8080:8080 \
--link selenoid \
aerokube/selenoid-ui:latest-release \
--selenoid-uri http://selenoid:4444

docker pull selenoid/video-recorder:latest-release
docker pull selenoid/chrome:latest

or 

start selenoid_project docker-compose up -d
