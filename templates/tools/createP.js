(function() {
        const id = crypto.randomUUID(); 
        var newP = document.createElement('p'); 
        newP.id = id;
        newP.textContent = 'Hello, I am a new p!'; 
        if (selectedElementId) {
            const container = document.getElementById(selectedElementId);
            container.appendChild(newP);
        } else {
            const container = document.querySelector('.container');
            container.appendChild(newP);
        }
})();
