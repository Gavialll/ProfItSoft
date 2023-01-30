import React, {Component} from 'react';
import '../style/display.css'

class OutputDisplay extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const {outputValue} = this.props
        return (
            <div className={"display_bottom"}>{outputValue}</div>
        );
    }
}

export default OutputDisplay;