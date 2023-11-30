//defining a class for the selected order to be sent back to server
class Order_Item {
    constructor(name) {
        this.name = name;
        this.selectedOptions = [];
        this.selectedAdditions = [];
    }
}

let orderItems = [];//array for order_item objects
const menuClassesURL = 'menu.json?restaurant=Budofol\'s Restaurant';//url for getting menu from server
const sendURL = 'http://localhost:8080/P3_war/OrderSent';

var coll = document.getElementsByClassName("Cart-collapsible");
var i;

for (i = 0; i < coll.length; i++) {
    coll[i].addEventListener("click", function() {
        this.classList.toggle("active");
        var content = this.nextElementSibling;
        if (content.style.display === "block") {
            content.style.display = "none";
        } else {
            content.style.display = "block";
        }
    });
}

function cartPrint(orderItems){
    let cartPrintHTML = document.createElement("ul");
    for (let j=0; j < orderItems.length; j++){
        let orderPrintItem = document.createElement("li");
        orderPrintItem.innerText = orderItems[j].name;
        let orderPrintItemExtra = document.createElement("ul");
        for (let q=0; q < orderItems[j].selectedOptions.length; q++){
            let orderPrintItemExtraOptions = document.createElement("li");
            orderPrintItemExtraOptions.innerText = orderItems[j].selectedOptions[q];
            orderPrintItemExtra.appendChild(orderPrintItemExtraOptions);
        }
        for (let q=0; q < orderItems[j].selectedAdditions.length; q++) {
            let orderPrintItemExtraAddition = document.createElement("li");
            orderPrintItemExtraAddition.innerText = orderItems[j].selectedAdditions[q];
            orderPrintItemExtra.appendChild(orderPrintItemExtraAddition);
        }
        orderPrintItem.appendChild(orderPrintItemExtra);
        cartPrintHTML.appendChild(orderPrintItem)
    }
    document.getElementById("JS-printer").innerHTML = "";
    document.getElementById("JS-printer").appendChild(cartPrintHTML);
}

document.getElementById("cart-button").addEventListener("click",() => cartPrint(orderItems));




//We could avoid having an EventListener if we just load the script after the HTML (so put it at the bottom of <body>)

document.addEventListener("DOMContentLoaded", function () {
    console.log('DOM content loaded');
    //unfinished fetch for getting menu from server
    fetch(menuClassesURL ,{

    })
        .then(response => {
            return response.json()})
        .then(Menu => {
            console.log(Menu);

            document.getElementById("menuContainer").innerHTML = HTMLgen(Menu);
        })
        .catch(error => {
            console.error('Error:', error);
        });

    //ittereates over option groups to handle checkbox selections
    let optionGroups = document.querySelectorAll('.option');
    optionGroups.forEach(function (optionGroup) {

        //get the minimum and maximum options form html
        let minSelections = optionGroup.getAttribute('data-min-selections');
        let maxSelections = optionGroup.getAttribute('data-max-selections');

        //adds event listener to each checkbox in the option group
        let checkboxes = optionGroup.querySelectorAll('input[type="checkbox"]');
        checkboxes.forEach(function (checkbox) {
            checkbox.addEventListener('change', function () {
                //checks if number of selected checkboxes exceeds the maximum allowed
                let checkedCount = optionGroup.querySelectorAll('input[type="checkbox"]:checked').length;
                if (checkedCount > maxSelections) {
                    alert('Maximum selections exceeded.');
                    checkbox.checked = false;
                }
            });
        });
    });

    //event listener for "add to order" buttons
    document.getElementById("menuContainer").addEventListener("click", function (event) {
        // Check if the clicked element has the "add-to-order" class
        if (event.target.classList.contains("add-to-order")) {
            // Handle the click on the "Add to Order" button
            console.log('Add to Order button clicked');

            // Find the closest item container
            let itemContainer = event.target.closest('.item-container');
            if (!itemContainer) {
                console.error('Could not find the closest .item-container element.');
                return;
            }

            // Find the option group within the item container
            let optionGroup = itemContainer.querySelector('.option');
            if (!optionGroup) {
                console.error('Could not find the .option element within the item container.');
                return;
            }

            //extracts item details and selected options and additions
            let itemName = itemContainer.querySelector('h3').textContent.trim();
            let selectedOptions = [];
            optionGroup.querySelectorAll('input[type="checkbox"]:checked').forEach(function (checkbox) {
                selectedOptions.push(checkbox.getAttribute('data-option'));
            });

            let selectedAdditions = [];
            itemContainer.querySelectorAll('.addition input[type="checkbox"]:checked').forEach(function (checkbox) {
                selectedAdditions.push(checkbox.getAttribute('data-addition'));
            });

            //get quantity form the input field on curret item
            let quantity = parseInt(itemContainer.querySelector('#item-quantity').value, 10);

            //creates the order_item objects and puts them in the orderItems array
            for (let i = 0; i < quantity; i++) {
                let orderItem = new Order_Item(itemName);
                orderItem.selectedOptions = selectedOptions.slice();
                orderItem.selectedAdditions = selectedAdditions.slice();
                orderItems.push(orderItem);
            }

            console.log(orderItems);
        }
    });

    // adds event listener for "send order" button
    let sendOrderButton = document.getElementById('send-order-button');
    sendOrderButton.addEventListener('click', function () {
        console.log('Send Order button clicked');
       // checks if there are orders in the array to send
        if (orderItems.length === 0) {
            console.log('No items in the order.');
            return;
        }

        //Json setup for the orderitems array
        let orderItemsJSON = orderItems.map(orderItem => ({
            name: orderItem.name,
            selectedOptions: orderItem.selectedOptions,
            selectedAdditions: orderItem.selectedAdditions
        }));

        //fetch for sending order to server(needs to be fleshed out)
        fetch(sendURL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(orderItemsJSON),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                // Handle success (if needed)
                console.log('Order sent successfully');
            })
            .catch(error => {
                // Handle errors
                console.error('Error sending order:', error);
            });
    });
})

function HTMLgen(Menu) {
    let html = '';
    console.log('Menu:', Menu);
    if (Menu && Menu.length > 0 && Menu[0].items) {
        Menu[0].items.forEach(item => {
            html += '<div class="item-container">';
            html += `<h3>${item.displayName}</h3>`;
            html += `<p>${item.basePrice / 100} kr</p>`;
            html += `<div class="option" data-min-selections="${item.minOptions}" data-max-selections="${item.maxOptions}">`;

            item.options.forEach(option => {
                html += '<div class="checkbox-container">';
                html += `<input type="checkbox" data-option="${option.displayName}">`;
                html += `<label> ${option.displayName}</label>`;
                html += '</div>';
            })
            html += '</div>';

            html += '<div class="addition">';

            item.additions.forEach(addition => {
                html += '<div class="checkbox-container">';
                html += `<input type="checkbox" data-addition="${addition.displayName}">`;
                html += `<label>${addition.displayName}</label>`;
                html += '</div>';
            })
            html += '</div>'

            html += '<label for="item-quantity">quantity:</label>';
            html += '<input type="number" id="item-quantity" value="1" min="1">';
            html += `<button class="add-to-order" data-item-name="${item.displayName}">Add to Order</button>`;
            html += '</div>';
        });
    } else {
        console.error('Menu or Menu[0].items is undefined');
    }

    return html;
}
