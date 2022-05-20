#docker run -p 5432:5432  --name monolith-db -e POSTGRES_PASSWORD=12345 -d postgres
docker run -p 5431:5432  --name pet-service-db -e POSTGRES_PASSWORD=12345 -d postgres
docker run -p 5430:5432  --name store-service-db -e POSTGRES_PASSWORD=12345 -d postgres
docker run -p 5429:5432  --name user-service-db -e POSTGRES_PASSWORD=12345 -d postgres

sleep "3"

#cat ./db.sql | docker exec -i monolith-db psql -U postgres
cat ./db.sql | docker exec -i pet-service-db psql -U postgres
cat ./db.sql | docker exec -i store-service-db psql -U postgres
cat ./db.sql | docker exec -i user-service-db psql -U postgres

sleep "3"

#docker restart monolith-db
docker restart pet-service-db
docker restart store-service-db
docker restart user-service-db

sleep "3"