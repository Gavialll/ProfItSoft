import React, {Component} from "react";
import Display from "./components/Display";
import {buttonsValue} from "./buttonsValue";
import {calculator} from "./Calculator";
import {getHistory, saveHistory} from "./HistoryService";
import './style/wrapper.css'
import './style/root.css'
import './style/button.css'
import {expressionReduce} from "./redusers/expressionReduse";
import * as Redux from "redux";
import Button from "@material-ui/core/Button";
import {Box} from "@material-ui/core";
import {
    END_OF_STATEMENT_IS_EQUALS,
    END_OF_STATEMENT_IS_SIMBL,
    IS_EQUALS,
    IS_MINUS,
    IS_NUMBER,
    IS_SIMBL,
    STATEMENT_IS_BAD,
    STATEMENT_IS_CORRECT,
} from "./RegEx";
import Buttons from "./components/Buttons";

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            inputValue: "",
            historyValue: [],
            status: false
        }
        this.store = Redux.legacy_createStore(expressionReduce)
    }

    callbackFromButton = (btnValue) => {
        this.selectAction(btnValue)
    }

    componentDidMount() {
        this.store.subscribe(() => {
            let expressions = this.store.getState().expressions
            for (let i = 0; i < expressions.length; i++) {
                let [first, simbl, second] = expressions[i].split(/\s+/)
                this.renderResult(calculator(first, simbl, second), {
                    inputValue: expressions[i]
                })
            }
            this.setState({
                ...this,
                historyValue: getHistory(),
                status: false
            })
        })
        this.setState({
            ...this.state,
            historyValue: getHistory(),
        })
    }

    componentDidUpdate(prevProps, prevState, s) {
        if (STATEMENT_IS_CORRECT.test(this.state.inputValue)) {
            let [firstNumber, simbl, secondNumber] = this.state.inputValue.split(/\s+/)
            let result = calculator(firstNumber, simbl, secondNumber)
            this.setState(this.renderResult(result, this.state))
        }
    }

    clearHistory = () => {
        this.setState({
            ...this,
            historyValue: []
        })
        localStorage.setItem("history", JSON.stringify([]))
    }

    clearState = () => {
        this.setState({
            ...this,
            inputValue: "",
            status: false
        })
    }

    countBackendStatements = () => {
        this.setState({
            ...this,
            status: true
        })
        fetch(`/statements?count=${Math.floor(Math.random() * 10)}`)
            .then((response) => {
                if ([404, 400, 500].includes(response.status)) {
                    return Promise.reject(response);
                }
                return response.json()
            })
            .then((data) => {
                this.store.dispatch({
                    type: "ADD",
                    expressions: data,
                })
            })
            .catch(error => {
                console.log(error)
                saveHistory("Error: server doesn't work")
                this.setState({
                    ...this,
                    historyValue: getHistory()
                })
            });
    }

    createHistoryStatement = (statement, result) => {
        if (END_OF_STATEMENT_IS_SIMBL.test(statement) || END_OF_STATEMENT_IS_EQUALS.test(statement)) {
            let newInputValue = statement.substring(0, statement.length - 3);
            return newInputValue.concat(" = ").concat(result)
        }
        return statement.concat(" = ").concat(result)
    }

    updateDisplay = (string) => {
        this.setState({
            ...this,
            inputValue: string,
        })
    }

    selectAction = (btnValue) => {

        let displayAddSimbl = (btnValue) => {
            this.updateDisplay(
                this.state.inputValue
                    .concat(" ")
                    .concat(btnValue)
                    .concat(" ")
            )
        }
        let displayAddNumber = (btnValue) => {
            this.updateDisplay(
                this.state.inputValue.concat(btnValue)
            )
        }
        let displayChangeSimbl = (btnValue) => {
            let newInputValue = this.state.inputValue.substring(0, this.state.inputValue.length - 3);
            this.updateDisplay(
                newInputValue
                    .concat(" ")
                    .concat(btnValue)
                    .concat(" ")
            )
        }

        let {inputValue} = this.state
        let statement = `${inputValue} ${btnValue} `

        if (btnValue === "C") {
            this.clearState()
        } else if (STATEMENT_IS_BAD.test(statement)) {
            return;
        } else if (END_OF_STATEMENT_IS_SIMBL.test(inputValue) && IS_SIMBL.test(btnValue)) {
            if (IS_EQUALS.test(btnValue)){
                return
            }
            displayChangeSimbl(btnValue)
        } else if (IS_NUMBER.test(btnValue) || (inputValue === "" && IS_MINUS.test(btnValue))) {
            displayAddNumber(btnValue)
        } else if (inputValue !== "" && IS_SIMBL.test(btnValue)) {
            displayAddSimbl(btnValue)
        }
    }

    renderResult = (result, state) => {
        let {inputValue} = state
        let endSimbl = inputValue.split(/\s+/)[3]

        if (result === Infinity) {
            let historyStatement = this.createHistoryStatement(inputValue, "Division by zero")
            return {
                ...state,
                inputValue: "Division by zero",
                historyValue: saveHistory(historyStatement),
                status: true
            }
        } else if ('*+-/'.includes(endSimbl)) {
            let historyStatement = this.createHistoryStatement(inputValue, result)
            return {
                ...state,
                inputValue: result.toString().concat(" ").concat(endSimbl).concat(" "),
                historyValue: saveHistory(historyStatement)
            }
        } else {
            let historyStatement = this.createHistoryStatement(inputValue, result)
            return {
                ...state,
                inputValue: result.toString(),
                historyValue: saveHistory(historyStatement)
            }
        }
    }

    render() {
        const {inputValue, historyValue, status} = this.state

        return (
            <Box className={"root"}>
                <Box className={"wrapper"}>
                    <Display outputValue={inputValue} historyArray={historyValue}/>
                    <Buttons buttonsValue={buttonsValue} returnValue={this.callbackFromButton} status={status} />
                    <Button classes={{root: "buttonOther"}}
                            variant={"outlined"}
                            color={"primary"}
                            onClick={() => this.clearHistory()}
                            disabled={status}
                    >Clear history
                    </Button>
                    <Button classes={{root: "buttonOther"}}
                            variant={"outlined"}
                            color={"primary"}
                            onClick={() => this.countBackendStatements()}
                            disabled={status}
                    >Get statements
                    </Button>
                </Box>
            </Box>
        );
    }
}

export default App;



