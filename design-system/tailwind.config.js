/**
 * Tailwind CSS Theme Configuration
 * AgroCalibre - Plantadeiras & Implementos Agrícolas
 */

/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,js,ts,jsx,tsx}",
    "./public/**/*.html"
  ],
  theme: {
    extend: {
      colors: {
        brand: {
          primary: '#CE0B10',         // Vermelho Institucional (Pantone 186C)
          'primary-hover': '#B0090D',
          'primary-dark': '#8D1913',  // Dark accent / borders
          'primary-deep': '#460E0F',  // Active surfaces / high contrast
          secondary: '#ECCA00',       // Amarelo Principal (Pantone 7405C)
          'secondary-warm': '#E7B104',// Amarelo Ouro/Trigo (Pantone 124C)
        },
        surface: {
          dark: '#373636',            // Cinza Escuro Técnico (K 92%)
          neutral: '#575756',         // Cinza K 80% (Texto secundário)
          border: '#C6C6C6',          // Cinza K 30% (Divisores)
          light: '#F8F9FA',           // Fundo principal de alta visibilidade
          pure: '#FFFFFF',            // Cards e modais
        },
        agri: {
          success: '#1E7E34',
          warning: '#ECCA00',
          danger: '#CE0B10',
          seed: '#E7B104',
          gear: '#575756',
        }
      },
      fontFamily: {
        sans: ['Montserrat', 'Myriad Pro', 'sans-serif'],
        display: ['Montserrat', 'sans-serif'],
      },
      fontWeight: {
        light: '300',
        regular: '400',
        semibold: '600',
        extrabold: '800',
        black: '900',
      },
      minHeight: {
        'touch': '48px',
        'touch-lg': '56px',
      },
      minWidth: {
        'touch': '48px',
        'touch-lg': '56px',
      },
      borderRadius: {
        'industrial': '16px',
        'industrial-sm': '10px',
        'industrial-xs': '6px',
      },
      boxShadow: {
        'field-card': '0 2px 8px rgba(55, 54, 54, 0.08)',
        'field-active': '0 4px 16px rgba(206, 11, 16, 0.16)',
      }
    },
  },
  plugins: [],
};

