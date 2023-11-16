let ArrayOpt = []
let ArrayAdd = []
let Menu = []
let AddArrCounter = 0
let OptArrCounter = 0
let MenuCounter = 0


function AddOption()
{
    let name =document.getElementById('OptName')
    let price = document.getElementById('OptPrice')
    let Allergy= [document.getElementById("vegan").value,document.getElementById("vegeterian").value, document.getElementById("glutenFree").value,document.getElementById("lactose").value,document.getElementById("fingerfood").value]
//I clearly use hannah montage linux because I fear no god with my methods of importing variables
    let description =document.getElementById("OptDesc")

    ArrayOpt[OptArrCounter] = {name:name.value,price:price.value,description:description.value,allergy:Allergy}
    OptArrCounter++
    writeTextArea("OptText",ArrayOpt)
    name.value = null
    price.value = null
    description.value = null

}

function AddAddtions()
{
    let name = document.getElementById("addName")
    let price = document.getElementById("addPrice")
    let description =document.getElementById("addDesc")
    ArrayAdd[AddArrCounter] = {name:name.value,price:price.value,description:description.value}
    AddArrCounter++
    writeTextArea("AddText",ArrayAdd)
    name.value = null
    price.value = null
    description.value = null
}


const AddOptToListButton = document.getElementById("AddOPtToListBut")
const AddAddToListButton =document.getElementById("AddAddTolistBut")
AddOptToListButton.addEventListener('click', (event) => {
    AddOption()
})

AddAddToListButton.addEventListener('click',(event) => {
    AddAddtions()
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
    document.getElementById("RevelOpt").checked = false
    document.getElementById("AddDiv").hidden = true
    document.getElementById("AddBox").checked = false
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

    MenuCounter = Menu.length
    Clearinfo()
}
function ImportMenu()
{
  //get menu from server
  Menu = thing
}

function GoToItem(Number)
{
    MenuCounter = Number
    ArrayAdd = Menu[Number].additions
    ArrayOpt = Menu[Number].options
    document.getElementById("DisplayName").value = Menu[Number].name
    document.getElementById("OrignalPrice").value = Menu[Number].basePrice
    writeTextArea("OptText",ArrayOpt)
    writeTextArea("AddText",ArrayAdd)
// set forms to Menu[Number]
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
            discount: {
            days: ["Monday", "Tuesday"],
                price: -1000
        }
    }
}

}

function writeTextArea(textId,array)
{
    let text = document.getElementById(textId)
    text.value = ""
    for(let i=0; array.length > i;i++)
    {
        array[i].name
        text.value += array[i].name + ": " + array[i].price + "\n"
    }
}

//When you click a button the respected function is called
document.getElementById("Done").addEventListener("click", (event)=>{SendMenuToServer})
document.getElementById("SetItemButton").addEventListener('click',(event)=>{GoToItem(document.getElementById("SetItemForm").value)})
//unlike these buttons who change a gobale varible and set value in froms
document.getElementById("GoToOptButton").addEventListener("click",(event)=>{
    OptArrCounter = document.getElementById("GoToOptForm").value
    document.getElementById("OptName").value = ArrayOpt[OptArrCounter].name
    document.getElementById("OptPrice").value = ArrayOpt[OptArrCounter].price
    document.getElementById("OptDesc").value = ArrayOpt[OptArrCounter].description


})
document.getElementById("GoToAddButton").addEventListener("click",(event)=>{
    AddArrCounter = document.getElementById("GoToAddForm").value
    document.getElementById("addName").value = ArrayAdd[AddArrCounter].name
    document.getElementById("addPrice").value = ArrayAdd[AddArrCounter].price
    document.getElementById("addDesc").value = ArrayAdd[AddArrCounter].description

})

//todo: add menu's to resturangs.
//todo: Delete things from array aka delete opt,add,items: ask vince for best method
//todo: Create menu from json
//todo: Send Jason sennan >:(
//name to menu, resturang name
//