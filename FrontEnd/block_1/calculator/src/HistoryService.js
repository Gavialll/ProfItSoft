
let historyKey = "history";

export let getHistory = () => JSON.parse(localStorage.getItem(historyKey));

export let saveHistory = (newItem) => {
    if(isHistory()){
        localStorage.setItem(historyKey, JSON.stringify([]))
        return
    }
    let history = getHistory()
    history.push(newItem)
    saveToLocalStorage(history)

    return history;
}

let saveToLocalStorage = (history) => localStorage.setItem(historyKey, JSON.stringify(history));
let isHistory = () => localStorage.getItem(historyKey) === null;