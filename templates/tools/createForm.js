// Функция для создания и стилизации формы
(function() {
    // Получаем контейнер, в который добавим форму
    const container = document.querySelector('.container');
  
    // Генерация уникального ID для формы
    const formId = crypto.randomUUID();

    // Создаем саму форму
    const form = document.createElement('form');
    form.setAttribute('id', formId);
    form.setAttribute('action', '#');
    form.setAttribute('method', 'POST');
  
    // Стили для формы
    form.style.maxWidth = '400px';
    form.style.margin = '0 auto';
    form.style.padding = '20px';
    form.style.border = '1px solid #ccc';
    form.style.borderRadius = '8px';
    form.style.backgroundColor = '#f9f9f9';
    form.style.boxShadow = '0 2px 10px rgba(0, 0, 0, 0.1)';
    
    // Генерация уникальных ID для input и label
    const inputId = crypto.randomUUID();
    const labelId = crypto.randomUUID();
    // Создаем поле для ввода имени
    const nameLabel = document.createElement('label');
    nameLabel.textContent = 'Имя:';
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
    
    // Создаем поле для ввода пароля
    const passwordLabel = document.createElement('label');
    passwordLabel.textContent = 'Пароль:';
    passwordLabel.setAttribute('for', 'password');
    passwordLabel.setAttribute('id', labelId); 
    
    const passwordInput = document.createElement('input');
    passwordInput.setAttribute('type', 'password');
    passwordInput.setAttribute('id', inputId);
    passwordInput.setAttribute('name', 'password');
    passwordInput.setAttribute('required', true);
    
    // Стили для поля ввода
    passwordInput.style.width = '100%';
    passwordInput.style.padding = '10px';
    passwordInput.style.marginBottom = '15px';
    passwordInput.style.border = '1px solid #ddd';
    passwordInput.style.borderRadius = '4px';
    passwordInput.style.boxSizing = 'border-box';
    
    
    // Создаем кнопку отправки
    const submitButton = document.createElement('button');
    const buttonId = crypto.randomUUID();

    submitButton.setAttribute('type', 'submit');
    submitButton.setAttribute('id', buttonId);
    submitButton.textContent = 'Отправить';
    
    // Стили для кнопки
    submitButton.style.padding = '10px 20px';
    submitButton.style.border = 'none';
    submitButton.style.backgroundColor = '#4CAF50';
    submitButton.style.color = 'white';
    submitButton.style.fontSize = '16px';
    submitButton.style.borderRadius = '4px';
    submitButton.style.cursor = 'pointer';
    
  
    // Добавляем все элементы формы в саму форму
    form.appendChild(nameLabel);
    form.appendChild(nameInput);
    form.appendChild(passwordLabel);
    form.appendChild(passwordInput);
    form.appendChild(submitButton);
  
    // Добавляем форму в контейнер
    
    if (selectedElementId) {
        const container = document.getElementById(selectedElementId);
        container.appendChild(form);
    } else {
        const container = document.querySelector('.container');
        container.appendChild(form);
    }
})();
  