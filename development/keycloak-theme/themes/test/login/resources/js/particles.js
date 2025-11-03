// particles.js - Floating particles animation for Keycloak Theme
/* Ref: https://www.subframe.com/tips/css-particle-background-examples */
document.addEventListener('DOMContentLoaded', function() {
    createParticles();
    addFadeInAnimation();
    addInputFocusEffects();
    addButtonFadeIn();
});

function createParticles() {
    let particlesContainer = document.getElementById('particles');
    if (!particlesContainer) {
        particlesContainer = document.createElement('div');
        particlesContainer.id = 'particles';
        particlesContainer.className = 'particles';
        document.body.appendChild(particlesContainer);
    }
    
    const particleCount = 15;
    
    for (let i = 0; i < particleCount; i++) {
        const particle = document.createElement('div');
        particle.classList.add('particle');
        
        // Use random positioning, to have flowing effect.
        const size = Math.random() * 20 + 5;
        const posX = Math.random() * 100;
        const posY = Math.random() * 100;
        const delay = Math.random() * 5;
        const duration = Math.random() * 3 + 3;
        
        particle.style.width = `${size}px`;
        particle.style.height = `${size}px`;
        particle.style.left = `${posX}%`;
        particle.style.top = `${posY}%`;
        particle.style.animationDelay = `${delay}s`;
        particle.style.animationDuration = `${duration}s`;
        
        // Set random colors, to have random colors on page floating...
        const colors = [
            'rgba(102, 126, 234, 0.4)',
            'rgba(118, 75, 162, 0.4)',
            'rgba(245, 40, 145, 0.4)'
        ];
        particle.style.background = colors[Math.floor(Math.random() * colors.length)];
        
        particlesContainer.appendChild(particle);
    }
}

function addFadeInAnimation() {
    const loginContainer = document.querySelector('.login-pf .container');
    if (loginContainer) {
        setTimeout(() => {
            loginContainer.classList.add('fade-in');
        }, 300);
    }
}

function addInputFocusEffects() {
    const inputs = document.querySelectorAll('.login-pf input[type="text"], .login-pf input[type="password"]');
    inputs.forEach(input => {
        input.addEventListener('focus', function() {
            this.style.boxShadow = '0 0 15px rgba(245, 40, 145, 0.5)';
        });
        
        input.addEventListener('blur', function() {
            this.style.boxShadow = 'none';
        });
    });
}

function addButtonFadeIn() {
    const button = document.querySelector('.login-btn');
    if (button) {
        button.style.opacity = '0';
        button.style.transition = 'opacity 1s ease';
        setTimeout(() => {
            button.style.opacity = '1';
        }, 600);
    }
}