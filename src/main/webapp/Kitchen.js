const container = document.getElementById('arrayContainer');
const KitchenUrl ="/P3_war/kitchen"


document.addEventListener("DOMContentLoaded", function (){
    setInterval(fetchOrders,20000);
})

function fetchOrders() {
    fetch(KitchenUrl)
        .then(response=>{
            if(!response.ok) {
                throw new Error('order fetch failed');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            if (data.message === "No order yet"){
                console.log("no orders available");
                //todo display message in UI
            }else{
                processOrders(data);
            }
        })
        .catch(error => {
            console.error('fetch error:', error);
        });
}

function generateHtmlContent(order) {
    // Generating HTML for options
    let optionsHtml = order.options.map(option => `<li>${option.internalName}</li>`).join('');

    // Generating HTML for additions
    let additionsHtml = order.additions.map(addition => `<li>${addition.internalName}</li>`).join('');

    // Generating HTML for each order item
    let orderItemsHtml = order.orders.map(orderItem => {
        return `
            <div class="order-item">
                <p>Item: ${orderItem.internalName}</p>
                <p>Comment: ${orderItem.comment || ''}</p>
            </div>
        `;
    }).join('');

    // Full order HTML
    return `
        <div class="order-card">
            <h3>Order ID: ${order.orderId}</h3>
            <p>Table ID: ${order.tableId}</p>
            <p>Options:</p><ul>${optionsHtml}</ul>
            <p>Additions:</p><ul>${additionsHtml}</ul>
            ${orderItemsHtml}
        </div>
    `;
}


function processOrders(orderJSON) {
    container.innerHTML ='';

    orderJSON.forEach(orderJSON =>{
       const gridItem = document.createElement('div');
       gridItem.className = 'grid-item';
       gridItem.innerHTML = generateHtmlContent(orderJSON);
       container.appendChild(gridItem);
    });
}