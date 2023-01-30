import React, {Component} from 'react';
import '../style/display.css'
import HistoryDisplay from "./HistoryDisplay";
import OutputDisplay from "./OutputDisplay";

class Display extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const {outputValue, historyArray} = this.props
        return (
            <div className={"display"}>
                <HistoryDisplay historyArray={historyArray}/>
                <hr className={"hr"}/>
                <OutputDisplay outputValue={outputValue} />
            </div>
        );
    }
}

export default Display;