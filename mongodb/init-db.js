// Select or create the database
db = db.getSiblingDB('booksdb');

// Verify if the database exists or create it
if (!db.getCollectionNames().includes('books')) {
    db.createCollection('books');
}

// Read the JSON file and insert data into the collection
const fs = require('fs');
const filePath = '/data/import/books.json';

try {
    const fileContent = fs.readFileSync(filePath, 'utf8');
    if (fileContent.trim()) {
        const data = JSON.parse(fileContent);
        if (Array.isArray(data) && data.length > 0) {
            // Remove the _id field from each object
            const cleanedData = data.map(({ _id, ...rest }) => rest);
            db.books.insertMany(cleanedData);
            print("Data inserted in booksdb");
        } else {
            print("JSON file is empty or not an array");
        }
    }
} catch (error) {
    print("Error :", error.message);
}

// Check if the collection exists
print("Collections in booksdb :", db.getCollectionNames());