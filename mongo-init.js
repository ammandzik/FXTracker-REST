db = db.getSiblingDB(‘stocks’);
db.createUser({
user: "admin",
pwd: “stocks1234",
roles: [
{ role: “readWrite”, db: “stocks” }]
});