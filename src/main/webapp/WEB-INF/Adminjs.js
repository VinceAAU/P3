let ArrayOpt = []
let ArrayAdd = []
let AddArrCounter = 0
let OptArrCounter = 0



function AddOption(name,price)
{
    ArrayOpt[OptArrCounter] = {name:name,price:price}
    OptArrCounter++
    //put it in a text list
}

function AddAddtions(name,price)
{
    ArrayAdd[AddArrCounter] = {name:name,price:price}
    AddArrCounter++
}


const AddOptToListButton = document.getElementById("AddOPtToListBut")
const AddAddToListButton =document.getElementById("AddAddTolistBut")
AddOptToListButton.addEventListener('click', (event) => {
    AddOption(document.getElementById('OptName').value, document.getElementById('OptPrice').value)
})

AddAddToListButton.addEventListener('click',(event) => {
    AddAddtions(document.getElementById("Addname"),document.getElementById("Addprice"))
})


// next 4 functs are 2 eventlisterens on check boxed which hides/reveals their repsected element on marked or unmarked
const OptCheck = document.getElementById('RevelOpt')
OptCheck.addEventListener('change', (event) => {
    if (event.currentTarget.checked) {
        RevealOpt(false)
    }
    else
    {
        RevealOpt(true)
    }
})

const addCheck = document.getElementById('AddBox')
addCheck.addEventListener('change', (event) => {
    if (event.currentTarget.checked) {
        RevealAdd(false)
    }
    else
    {
        RevealAdd(true)
    }
})
function RevealOpt(input)
{
    let opt = document.getElementById("opt")
    opt.hidden = input
}

function RevealAdd(input)
{
 let add = document.getElementById("AddDiv")
    add.hidden = input
}
