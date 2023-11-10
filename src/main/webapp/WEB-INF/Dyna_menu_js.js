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

  console.log('item added to order', orderItem);
}

document.querySelector('div > button.add-to-order').addEventListener('click', (event) => {
    const target = event.target;

 
  if (target.classList.contains('add-to-order')) {
    const itemName = target.getAttribute('data-item-name');
    const optionsgroup = document.getAttribute('.options');
    const selecetedOptions = getSelectedOptions(optionsGroup);
    const additionGroup = document.querySelector('.addition');
    const selectedAdditions = getSelectedAdditions(additionGroup);
    
    addToOrder(itemName,selectedOptions,selectedAdditions);
  }
  function get Selectedoptions(optionsGroup) {
    const buttons = optionGroup.querySelectorall('button');
    const selectedOptions =[];

    buttons.forEach((buttons) => {
      if (buttins.classList.contains('selected')){
        const option = button.getAttribute('data-option');
        selectedOptions.push(option);
      }
    }); 

    return selectedOptions;
  }

  function getSelectedAdditions(additionGroup) {
  const buttons = additionGroup.querySelectorAll('button');
  const selectedAdditions = [];

  buttons.forEach((button) => { if (button.classList.contains('selected')) {
      const addition = button.getAttribute('data-addition');
      selectedAdditions.push(addition);
  });
  return selectedAdditions;
  }
    
  
  

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
