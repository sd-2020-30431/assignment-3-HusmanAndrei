import React from 'react';

export default class BadReportDecorator extends React.Component{

    render(){
        return (
            <div style={{backgroundColor: 'red'}}>{this.props.value}</div>
        )
    }

}