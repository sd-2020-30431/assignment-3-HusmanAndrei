import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import IdealReportDecorator from './decorators/IdealReportDecorator';
import BadReportDecorator from './decorators/BadReportDecorator';

const styles = {
    root: {
        minWidth: 275,
        backgroundColor: 'antiquewhite',

    },
    bullet: {
        display: 'inline-block',
        margin: '0 2px',
        transform: 'scale(0.8)',
    },
    title: {
        fontSize: 14,
    },
    pos: {
        marginBottom: 12,
    },
};

export default class Report extends React.Component {


    chooseReport(value){
        if (value < 200){
            return <IdealReportDecorator value={value}/>
        }
        else{
            return <BadReportDecorator value={value}/>
        }
    }

    render() {

        const { weekQuantity, monthQuantity,  weekCalories, monthCalories } = {...this.props.report};


        return (
            <Card style={styles.root} variant="outlined">
                <CardContent>
                    <Typography style={styles.title} color="textSecondary" gutterBottom>
                        We have compiled a series of reports to see how well you manage your items
                    </Typography>
                    <Typography variant="h5" component="h2">
                        Waste management
                    </Typography>
                    <Typography style={styles.pos} color="textSecondary">
                        Calories wasted this month: {this.chooseReport(monthCalories)}
                    </Typography>
                    <Typography style={styles.pos} color="textSecondary">
                        Calories wasted this week: {this.chooseReport(weekCalories)}
                    </Typography>
                    <Typography style={styles.pos} color="textSecondary">
                        Quantity wasted this month: {this.chooseReport(monthQuantity)}
                    </Typography>
                    <Typography style={styles.pos} color="textSecondary">
                        Quantity wasted this week: {this.chooseReport(weekQuantity)}
                    </Typography>

                </CardContent>

            </Card>
        );
    }
}