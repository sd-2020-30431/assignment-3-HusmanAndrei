import React from 'react';
import httpInstance from "../services/httpService";
import GroceryList from "../components/GroceryList";
import {MuiThemeProvider} from 'material-ui'
import RaisedButton from "material-ui/RaisedButton";
import AddItem from "../components/AddItem";
import CalendarSelect from "../components/CalendarSelect";
import GroceryCard from "../components/GroceryCard";
import Report from "../components/Report";
import Connecter from "../components/Connecter";
import DateComponent from "../components/DateComponent";


export default class HomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            groceryList: [],
            selectedItem: null,
            report: {
                weekCalories: -1,
                monthCalories: -1,
                weekQuantity: -1,
                monthQuantity: -1
            },
            currentDate: new Date()
        }

    }

    async componentDidMount() {
        const id = localStorage.getItem('token');
        const groceries = await httpInstance.getGroceries(id);
        this.setState({
            groceryList: groceries
        });
        await this.fetchReports(id)
        await this.getCurrentDate();
    }

    async fetchReports(id){
        const reports = await httpInstance.getReports(id);
        this.setState({
            report: reports
        });
    }

    validateDates(purchase, consumption, expiration) {
        return purchase <= consumption <= expiration;
    }


    handleSocket(){

        // eslint-disable-next-line no-undef




        // sock.onopen = () => {
        //     console.log('open');
        //     sock.send('test');
        // };
        //
        // sock.onmessage = (e) => {
        //     console.log('message', e.data);
        //     sock.close();
        // };
        //
        // sock.onclose = () => {
        //     console.log('close');
        // };
    }

    async add(groceryItem) {
        const id = localStorage.getItem('token');

        const returnedItem = await httpInstance.addGrocery(id, groceryItem);
        console.log("Ret add ", returnedItem)
        if (typeof returnedItem === "string") {
            alert(returnedItem);
        } else {
            const newItems = this.state.groceryList;
            newItems.push(returnedItem);
            this.setState({
                groceryList: newItems
            })
            await this.fetchReports(id)
        }
    }

    selectItem(item){
        this.setState({
            selectedItem: item
        })
    }

    async donateItem(itemId){
        const id = localStorage.getItem('token');
        const response = await httpInstance.donateItem(id, itemId);
        if(this.state.selectedItem.id === itemId){
            this.setState({
                selectedItem : null
            })
        }
        const newItems = this.state.groceryList.filter(item => item.id !== itemId);
        this.setState({
            groceryList: newItems
        });
        await this.fetchReports(id);
    }

    async getCurrentDate(){
        const currentDate = await httpInstance.getCurrentDate();
        this.setState({
            currentDate : currentDate
        });

    }

    async changeDate(newDate){
        const serverDate = await httpInstance.changeDate(newDate);
        this.setState({
            currentDate: serverDate
        })

    }
    async messageReceived(message){
        await this.getCurrentDate();
        await this.fetchReports(localStorage.getItem('token'));
    }


    render() {
        return (
            <MuiThemeProvider>
                <DateComponent currentDate={this.state.currentDate} onChange={this.changeDate.bind(this)}/>
                <div style={{display: 'flex', flexDirection: 'column', paddingBottom: 40}}>
                    <div style={{
                        display: 'flex',
                        height: '100%',
                        width: '100%',
                        alignItems: 'center',
                        justifyContent: 'space-around',
                    }}>
                        <div style={{flexGrow: 2, margin: 30}}>
                            <h3> My groceries</h3>

                            <GroceryList items={this.state.groceryList} headers={["Name", "Quantity", "Calories"]} onSelect={this.selectItem.bind(this)}/>
                        </div>
                        <div style={{flexGrow: 1}}>
                            <AddItem add={this.add.bind(this)}/>
                        </div>
                    </div>
                    <div style={{display: 'flex', justifyContent: 'space-around', marginLeft: 30}}>
                        <div style={{flexGrow: 1}}>
                        <h3>Selected grocery</h3>
                    {this.state.selectedItem !== null ? <GroceryCard item={this.state.selectedItem} donateItem={this.donateItem.bind(this)} /> : null}
                        </div>
                        <div style={{flexGrow: 1, padding: 30}}>
                            <h3>Reports</h3>
                            <Report report={this.state.report}  />
                        </div>
                        <Connecter messageReceived={this.messageReceived.bind(this)}/>
                    </div>
                </div>
            </MuiThemeProvider>

        );
    }
}