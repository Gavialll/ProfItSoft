import React, {Component} from 'react';
import ButtonLine from "./ButtonLine";

class Buttons extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const {buttonsValue, status, returnValue} = this.props
        return (
            <div>
            {buttonsValue.map((value, index) =>
                    <ButtonLine key={index} values={value} click={returnValue} status={status}/>)
            }
            </div>
        );
    }
}

export default Buttons;