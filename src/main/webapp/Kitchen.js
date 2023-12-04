const container = document.getElementById('arrayContainer');
const KitchenUrl ="/P3_war/kitchen"
const updateOrderStatusUrl = '/P3_war/updateOrderStatus';

document.addEventListener("DOMContentLoaded", function (){
    fetchOrders();
    setInterval(fetchOrders,7500);
})

function fetchOrders() {
    fetch(KitchenUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error('Order fetch failed');
            }
            return response.json();
        })
        .then(data => {
            if (data.message) {
                container.innerHTML = `<p class="no-orders-message">${data.message}</p>`;
                container.classList.add("center-content");
            } else {
                container.classList.remove("center-content");
                processOrders(data);
            }
        })
        .catch(error => {
            console.error('Fetch error:', error);
            container.innerHTML = '<p>Error fetching orders.</p>';
        });
}

function generateHtmlContent(order) {
    // Generating HTML for each order item
    let orderItemsHtml = order.orders.map(orderItem => {
        // Generating HTML for options of this order item
        let optionsHtml = orderItem.options.map(option => `<li>${option.internalName}</li>`).join('');

        // Generating HTML for additions of this order item
        let additionsHtml = orderItem.additions.map(addition => `<li>${addition.internalName}</li>`).join('');

        // Order Item HTML Content
        return `
            <div class="order-item">
                <p>Bestiling: ${orderItem.internalName}</p>
                ${optionsHtml ? `<p>Variant(er):</p><ul>${optionsHtml}</ul>` : ''}
                ${additionsHtml ? `<p>Tilkøb:</p><ul>${additionsHtml}</ul>` : ''}
                <p>Kommentar: ${orderItem.comment || ''}</p>
            </div>
        `;
    }).join('');

    // Full order HTML
    return `
        <div class="order-card">
            <h3>Order nummer: ${order.orderId}</h3>
            <p>Bord: ${order.tableId}</p>
            ${orderItemsHtml}
            <button class="deliver-btn" data-order-id="${order.orderId}">Marker som færdig</button>
        </div>
    `;
}


function markOrderAsDelivered(orderId, orderElement) {
    console.log("Marking order as delivered:", orderId);

    fetch(updateOrderStatusUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ orderId: orderId, delivered: true })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to update order status');
            }
            return response.json();
        })
        .then(data => {
            console.log('Order status updated:', data);
            // Remove the order element from the display
            container.removeChild(orderElement);
        })
        .catch(error => {
            console.error('Error updating order status:', error);
        });
}
function processOrders(orderJSON) {
    container.innerHTML = '';

    orderJSON.forEach(order => {
        const gridItem = document.createElement('div');
        gridItem.className = 'grid-item';
        gridItem.innerHTML = generateHtmlContent(order);
        container.appendChild(gridItem);

        // Attach an event listener to the "Mark as Delivered" button
        const deliverButton = gridItem.querySelector(`.deliver-btn[data-order-id="${order.orderId}"]`);
        deliverButton.addEventListener('click', function() {
            markOrderAsDelivered(order.orderId, gridItem);
        });
    });
}