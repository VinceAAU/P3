class Order_Item {
  constructor(name) {
    this.name = name;
    this.selectedOptions = [];
    this.selectedAdditions = [];
  }
}

const order = [];


function addToOrder(itemName, selectedOptions, selectedAdditions) {
  const orderItem = new Order_Item(itemName);
  orderItem.selectedOptions = selectedOptions;
  orderItem.selectedAdditions = selectedAdditions;
  order.push(orderItem);
}

document.getElementById('menu').addEventListener('click', (event) => {
  const target = event.target;

 
  if (target.classList.contains('add-to-order')) {
    const itemName = target.getAttribute('data-item-name');
    addToOrder(itemName);
  }

}); 

const optionsGroup = document.querySelector('.options'); 
if (optionsGroup) {
  const buttons = optionsGroup.querySelectorAll('button');
  const minSelections = parseInt(optionsGroup.getAttribute('data-min-selections'));
  const maxSelections = parseInt(optionsGroup.getAttribute('data-max-selections'));

  const selectedOptions = new Set();

  buttons.forEach((button) => {
    button.addEventListener('click', () => {
      const option = button.getAttribute('data-option');

      if (selectedOptions.has(option)) {
        selectedOptions.delete(option);
        button.classList.remove('selected');
      } else {
        if (selectedOptions.size < maxSelections) {
          selectedOptions.add(option);
          button.classList.add('selected');
        }
      }

    });
  });
}

const additionGroup = document.querySelector('.addition'); 
if (additionGroup) {
  const buttons = additionGroup.querySelectorAll('button');

  buttons.forEach((button) => {
    button.addEventListener('click', () => {
    });
  });
}
