(function() {
     // Создаем input с типом button
     const button = document.createElement('input');
     button.setAttribute('type', 'button');
     button.setAttribute('value', 'Перейти');
     button.setAttribute('onclick', "window.location='#'");
   
     // Уникальный ID для кнопки (по желанию)
     const buttonId = crypto.randomUUID();
     button.setAttribute('id', buttonId);
   
     // Стили для кнопки
     button.style.padding = '10px 20px';
     button.style.fontSize = '16px';
     button.style.backgroundColor = '#4CAF50';
     button.style.color = 'white';
     button.style.border = 'none';
     button.style.borderRadius = '4px';
     button.style.cursor = 'pointer';
   
     // Изменение цвета кнопки при наведении
     button.addEventListener('mouseover', () => {
       button.style.backgroundColor = '#45a049';
     });
     button.addEventListener('mouseout', () => {
       button.style.backgroundColor = '#4CAF50';
     });
   
     if (selectedElementId) {
        const container = document.getElementById(selectedElementId);
        container.appendChild(button);
    } else {
        const container = document.querySelector('.container');
        container.appendChild(button);
    }
  
})();
  