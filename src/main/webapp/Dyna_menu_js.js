class Order_Item {
  constructor(name) {
    this.name = name;
    this.selectedOptions = [];
    this.selectedAdditions = [];
  }
}

document.addEventListener("DOMContentLoaded", function () {
  let optionGroups = document.querySelectorAll('.option');

  optionGroups.forEach(function (optionGroup) {
    let minSelections = optionGroup.getAttribute('data-min-selections');
    let maxSelections = optionGroup.getAttribute('data-max-selections');

    let checkboxes = optionGroup.querySelectorAll('input[type="checkbox"]');

    checkboxes.forEach(function (checkbox) {
      checkbox.addEventListener('change', function () {
        let checkedCount = optionGroup.querySelectorAll('input[type="checkbox"]:checked').length;

        if (checkedCount > maxSelections) {
          alert('Maximum selections exceeded.');
          checkbox.checked = false;
        }
      });
    });
  });
});

document.addEventListener("DOMContentLoaded", function () {
  let addToOrderButtons = document.querySelectorAll('.add-to-order');

  addToOrderButtons.forEach(function (addToOrderButton) {
    addToOrderButton.addEventListener('click', function () {
      // Find the closest item container
      let itemContainer = addToOrderButton.closest('.item-container');

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

      let itemName = itemContainer.querySelector('h3').textContent.trim();

      let selectedOptions = [];
      optionGroup.querySelectorAll('input[type="checkbox"]:checked').forEach(function (checkbox) {
        selectedOptions.push(checkbox.getAttribute('data-option'));
      });

      let selectedAdditions = [];
      itemContainer.querySelectorAll('.addition input[type="checkbox"]:checked').forEach(function (checkbox) {
        selectedAdditions.push(checkbox.getAttribute('data-addition'));
      });

      let quantity = parseInt(itemContainer.querySelector('#item-quantity').value, 10);

      let orderItems = [];
      for (let i = 0; i < quantity; i++) {
        let orderItem = new Order_Item(itemName);
        orderItem.selectedOptions = selectedOptions.slice();
        orderItem.selectedAdditions = selectedAdditions.slice();
        orderItems.push(orderItem);
      }

      console.log(orderItems);
    });
  });
});
function HTMLgen(Menu_JSON){

  let html = '';

  MenuJSON.Menu.Category.forEach(cat => {
    html += '<div class="item-container">';
    html += `<h3>${cat.name}</h3>`;
    html += `<p>${cat.basePrice / 100} kr</p>`;
    html += `<div class="option" data-min-selections="${cat.minOptions}" data-max-selections="${cat.maxOptions}">`;

      cat.Menuitem.forEach(item => {
        if(item.option) {
          html += '<div class="checkbox-container">';
          html += `<input type="checkbox" data-option="${item.name}">`;
          html += `<label> ${item.name}</label>`;
          html += '</div>';
        }
      })
    html += '</div>';

    html += '<div class="addition">';

      cat.Menuitem.forEach(item => {
        if (!item.option) {

          html += '<div class="checkbox-container">';
          html += `<input type="checkbox" data-addition="${item.name}">`;
          html += `<label>${item.name}</label>`;
          html += '</div>';
        }
      })
    html += '</div>'

    html += '<label for="item-quantity">quantity:</label>';
    html += '<input type="number" id="item-quantity" value="1" min="1">';
    html += `<button class="add-to-order" data-item-name="${cat.name}">`;
    html += '</div>';
  })
  return html;
}