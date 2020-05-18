const moment = require("moment")

class HttpService{

    constructor() {
        this.url = "http://localhost:8080";
    }

    async initLogin(username, password){
        const response = await fetch(`${this.url}/login`, {
            body: JSON.stringify({
                name: username,
                password: password
            }),
            method: 'POST',
            headers:  {'Content-Type' : 'application/json' }
        }).then(response => response.json());
        return response;
    }

    async getGroceries(userId){
        const response = await fetch(`${this.url}/grocery`,{
            headers: {
                'Authorization' : `Bearer ${userId}`,
                'Content-Type' : 'application/json'
            }
        }).then(response => response.json());
        const mapped = response.map(r => this.changeBackTimeFormat(r));
        console.log('items', response)

        return mapped;
    }

    async addGrocery(userId, groceryItem){
        const newItem =  this.changeTimeFormat(groceryItem);
        const serialized = JSON.stringify(newItem);
        console.log("serialized", serialized)
        const response = await fetch(`${this.url}/grocery`,{
            headers: {
                'Authorization' : `Bearer ${userId}`,
                'Content-Type' : 'application/json'
            },
            body : serialized,
            method : 'POST'
        })
            .then(resp => resp.json());
        console.log("RESP", response)
        if( response.id == null){
            return response.message;
        }
        const formatted = this.changeBackTimeFormat(response)

        return formatted;
    }

    async getReports(userId){
        const response = await fetch(`${this.url}/stats/all`,{
            headers: {
                'Authorization' : `Bearer ${userId}`,
                'Content-Type' : 'application/json'
            }
        }).then(resp => resp.json());
        return response;

    }

    async donateItem(userId, itemId){
        console.log('item id', itemId)
        const response = await fetch(`${this.url}/donate?itemId=${itemId}`,{
            headers: {
                'Authorization' : `Bearer ${userId}`,
                'Content-Type' : 'application/json'
            },
            method: 'DELETE'
        }).then(resp => resp.json());
        return response;
    }


    async getCurrentDate(){
        const response = await fetch(`${this.url}/date`,{
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(resp => resp.text());
        return this.stringToDate(response);
    }

    async changeDate(newDate){

        const response = await fetch(`${this.url}/date?newDate=${newDate.toLocaleDateString()}`,{
            headers: {
                'Content-Type' : 'application/json'
            },
            method: 'PUT'
        }).then(resp => resp.text());
        console.log("server resp", response)
        return this.stringToDate(response);
    }

    stringToDate(dateString){
        if (dateString === null){
            return null;
        }
        let split = dateString.split('/')
        let month = parseInt(split[0]) - 1;
        let day = parseInt(split[1]);
        let year = parseInt(split[2]);
        return new Date(year, month, day)
    }



    changeTimeFormat(groceryItem){
        const newItem = JSON.parse(JSON.stringify(groceryItem));
        newItem.purchaseDate = groceryItem.purchaseDate.toLocaleDateString();
        if(groceryItem.consumptionDate !== null){
            newItem.consumptionDate = groceryItem.consumptionDate.toLocaleString();

        }
        else{
            newItem.consumptionDate = null;
        }
        newItem.expirationDate = groceryItem.expirationDate.toLocaleDateString();
        console.log("newi", newItem)
        return newItem;
    }

    changeBackTimeFormat(groceryItem){
        if(groceryItem === null){
            return null;
        }
        const newItem = JSON.parse(JSON.stringify(groceryItem))
        newItem.purchaseDate = this.stringToDate(groceryItem.purchaseDate);
        newItem.consumptionDate =  this.stringToDate(groceryItem.consumptionDate);
        newItem.expirationDate = this.stringToDate(groceryItem.expirationDate);
        return newItem;
    }



}
const httpInstance = new HttpService();
export default httpInstance;