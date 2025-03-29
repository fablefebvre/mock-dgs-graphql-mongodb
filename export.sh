#!/bin/bash
docker exec -it mock-dgs-graphql-mongodb-mongodb-1 mongoexport --db booksdb --collection books --out /data/import/books.json --jsonArray --pretty