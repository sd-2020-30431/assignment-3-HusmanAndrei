import React from 'react';
import SockJsClient from 'react-stomp';

export default class Connecter extends React.Component {
    constructor(props) {
        super(props);
    }

    sendMessage = (msg) => {
        console.log('clicked')
        this.clientRef.sendMessage('/app/hello', JSON.stringify({name: msg, userId: this.getId()}));
    }
    getId(){
        return localStorage.getItem("token");
    }

    handleMessage(mes){
        this.props.messageReceived();
        if(mes.type === 'individual'){
            alert(mes.content)
        }
    }

    render() {
        return (
            <div>
                <SockJsClient url='http://localhost:8080/gs-guide-websocket' topics={['/topic/greetings/' + this.getId(), '/topic/greetings']}
                              onMessage={this.handleMessage.bind(this)}
                              ref={ (client) => { this.clientRef = client }} />
                              
            </div>
        );
    }
}