let ArrayOpt = []
let ArrayAdd = []
let Menu = []
let AddArrCounter = 0
let OptArrCounter = 0
let MenuCounter = 0


function AddOption(name,price)
{
    let Allergy= [document.getElementById("vegan").value,document.getElementById("vegeterian").value, document.getElementById("glutenFree").value,document.getElementById("lactose").value,document.getElementById("fingerfood").value]
//I clearly use hannah montage linux because I fear no god with my methods of importing variables
    let description =document.getElementById("OptDesc")

    ArrayOpt[OptArrCounter] = {Name:name.value,Price:price.value,description:description.value,allergy:Allergy}
    OptArrCounter++
    document.getElementById("OptText").value += name.value+": "+ price.value + "\n"
    name.value = null
    price.value = null
    description.value = null

}

function AddAddtions(name,price)
{
    let description =document.getElementById("AddDesc")
    ArrayAdd[AddArrCounter] = {Name:name.value,Price:price.value,description:description.value}
    AddArrCounter++
    document.getElementById("AddText").value += name.value+": "+ price.value + "\n"
    name.value = null
    price.value = null
    description.value = null
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
document.getElementById("Done").addEventListener("click",(event)=>{
    SendMenuToServer()
})
document.getElementById("SubmitBut").addEventListener("click",(event)=>{
ContructMenu()
})
function ContructMenu()
{
    Menu[MenuCounter] =
        {
            name:document.getElementById("InternalName").value,
            DisplayName:document.getElementById("DisplayName").value, //not needed?
            basePrice:document.getElementById("OrignalPrice").value,
            minOptions:0,
            maxOptions:0,
            options:ArrayOpt,
            additions:ArrayAdd
        }

    MenuCounter++
    Clearinfo()
}



function SendMenuToServer()
{
    let json = {
    menu: {
        menuId: document.getElementById("MenuId"),
            availableTimes: {
            start: document.getElementById("StartTime"),
                end: document.getElementById("Apocalypse"),
                days: ["Monday", "Tuesday"] //GRRRRRR add html for this
        },
        items:Menu
        ,
            "discount": {
            "days": ["Monday", "Tuesday"],
                "price": -1000
        }
    }
}



}