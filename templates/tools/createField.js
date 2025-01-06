(function() {
    // Получаем контейнер, в который добавим форму
    const container = document.querySelector('.container');
    
    // Генерация уникальных ID для input и label
    const inputId = crypto.randomUUID();
    const labelId = crypto.randomUUID();
    // Создаем поле для ввода имени
    const nameLabel = document.createElement('label');
    nameLabel.textContent = 'Название:';
    nameLabel.setAttribute('for', 'name');
    nameLabel.setAttribute('id', labelId); 
    
    const nameInput = document.createElement('input');
    nameInput.setAttribute('type', 'text');
    nameInput.setAttribute('name', 'name');
    nameInput.setAttribute('required', true);
    nameInput.setAttribute('id', inputId); 
    
    // Стили для поля ввода
    nameInput.style.width = '100%';
    nameInput.style.padding = '10px';
    nameInput.style.marginBottom = '15px';
    nameInput.style.border = '1px solid #ddd';
    nameInput.style.borderRadius = '4px';
    nameInput.style.boxSizing = 'border-box';
    
    // Стили для label
    nameLabel.style.display = 'block';
    nameLabel.style.marginBottom = '5px';
    
    
  
    // Добавляем форму в контейнер
    
    if (selectedElementId) {
        const container = document.getElementById(selectedElementId);
        container.appendChild(nameLabel);
        container.appendChild(nameInput);
    } else {
        const container = document.querySelector('.container');
        container.appendChild(nameLabel);
    container.appendChild(nameInput);
    }
    
})();
  