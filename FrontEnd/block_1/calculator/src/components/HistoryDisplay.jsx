import React, {Component, createRef} from 'react';
import '../style/display.css'

class HistoryDisplay extends Component {
    constructor(props) {
        super(props);
        this.displayTop = createRef();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        this.displayTop.current
            .scrollTo(this.displayTop.current.scrollTop,  this.displayTop.current.scrollHeight);
    }

    render() {
        const {historyArray} = this.props

        return (
            <div ref={this.displayTop} className={"display_top"}>{
                    historyArray.map((item, index) => <p key={index}>{item}</p>)
            }</div>
        );
    }
}

export default HistoryDisplay;

