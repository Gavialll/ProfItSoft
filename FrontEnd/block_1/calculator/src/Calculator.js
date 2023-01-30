export function calculator(firstNumber, simbl, secondNumber){
    switch (simbl) {
        case "+": return +firstNumber + +secondNumber
        case "-": return +firstNumber - +secondNumber
        case "/": return +firstNumber / +secondNumber
        case "*": return +firstNumber * +secondNumber
        default: return null
    }
}