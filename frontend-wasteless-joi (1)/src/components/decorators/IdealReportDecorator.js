import React from 'react';

export default class IdealReportDecorator extends React.Component{

    render(){
        return (
            <div style={{backgroundColor: 'green'}}>{this.props.value}</div>
        )
    }

}