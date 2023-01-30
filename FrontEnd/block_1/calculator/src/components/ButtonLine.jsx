import React, {Component} from 'react';
import Button from "@material-ui/core/Button";
import {ButtonGroup} from "@material-ui/core";
import '../style/button.css'

class ButtonLine extends Component {

    render() {
        const {values, click, status} = this.props
        return (
            <React.Fragment>
                <ButtonGroup classes={{root: "group"}} size="large" aria-label="large button group">{
                    values.map(value => createButton(value, click, status))
                }</ButtonGroup>
            </React.Fragment>
        );
    }
}

const orange = {
    backgroundColor: "orange"
};

const gray = {
    backgroundColor: "white"
};

function createButton(btnValue, click, status) {
    return <Button key={btnValue}
                   classes={{ root: 'button' }}

                   style={"+-*=C/".includes(btnValue) ? orange : gray}
                       disabled={"C".includes(btnValue) ? false : status}
                       variant={"outlined"}
                       color={"primary"}
                       onClick={() => click(btnValue)}
                       >
                   {btnValue}
                       </Button>
                   }

                   export default ButtonLine;
