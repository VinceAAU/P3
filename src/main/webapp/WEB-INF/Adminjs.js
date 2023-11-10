function SubmitObjectToServer()
{
    return "niceguy"
}


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
