let ArrayOpt = []
let ArrayAdd = []
let Menu = []
let AddArrCounter = 0
let OptArrCounter = 0
let MenuCounter = 0


function AddOption(name,price)
{
    ArrayOpt[OptArrCounter] = {Name:name.value,Price:price.value}
    OptArrCounter++
    document.getElementById("OptText").value += name.value+": "+ price.value + "\n"
    name.value = null
    price.value = null
}

function AddAddtions(name,price)
{
    ArrayAdd[AddArrCounter] = {Name:name.value,Price:price.value}
    AddArrCounter++
    document.getElementById("AddText").value += name.value+": "+ price.value + "\n"
    name.value = null
    price.value = null
}


const AddOptToListButton = document.getElementById("AddOPtToListBut")
const AddAddToListButton =document.getElementById("AddAddTolistBut")
AddOptToListButton.addEventListener('click', (event) => {
    AddOption(document.getElementById('OptName'), document.getElementById('OptPrice'))
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
function Clearinfo()
{
    document.getElementById("DisplayName").value = null
    document.getElementById("InternalName").value = null
    document.getElementById("OrignalPrice").value = null
    document.getElementById("opt").hidden = true
    document.getElementById("RevelOpt").value = false
    document.getElementById("AddDiv").hidden = true
    document.getElementById("AddBox").value = false
    document.getElementById("AddText").value = null
    document.getElementById("OptText").value = null
    ArrayOpt = []
    ArrayAdd = []
    AddArrCounter = 0
    OptArrCounter = 0

}

function ContructMenu()
{
    Menu[MenuCounter] =
        {
            InternalName:document.getElementById("InternalName").value,
            DisplayName:document.getElementById("DisplayName").value,
            Price:document.getElementById("OrignalPrice").value,
            Options:ArrayOpt,
            Addtions:ArrayAdd
        }
    MenuCounter++
    Clearinfo()
}

function SendMenuToServer()
{

}