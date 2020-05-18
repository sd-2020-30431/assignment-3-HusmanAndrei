import * as React from "react";
import {getTime} from "../misc/time-handle";
import CalendarSelect from "./CalendarSelect";
import RaisedButton from "material-ui/RaisedButton";
import {MuiThemeProvider} from "material-ui";

export default class DateComponent extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
            seeChangeDate: false,
            currentDate: this.props.currentDate
        }
    }


    openSelect(){
        this.setState({
            seeChangeDate: true
        })
    }


    render() {
        const currentDate = this.props.currentDate;
        return(
            <div>
                <div>
                Current Date: {getTime(currentDate)}
                </div>
                <div>
                    <CalendarSelect isOpened={this.state.seeChangeDate} onClose={() => this.setState({seeChangeDate: false})}
                                selectedDate={this.state.currentDate} onChange={this.props.onChange}
                    />
                    <MuiThemeProvider>
                        <RaisedButton label="Select" primary={true}  onClick={this.openSelect.bind(this)}/>
                    </MuiThemeProvider>
                </div>
            </div>
        );
    }

}