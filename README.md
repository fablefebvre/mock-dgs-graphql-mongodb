TODO
docker build -t mock-dgs-graphql-mongodb .

# Data management with MongoDB
# Start MongoDB with initial data load
docker-compose up -d

# export data after modification
docker exec -it mock-dgs-graphql-mongodb-mongodb-1 mongoexport --db booksdb --collection books --out /data/import/books.json --jsonArray --pretty

# close MongoDB
docker-compose down