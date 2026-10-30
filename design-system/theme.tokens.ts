/**
 * AgroCalibre - Design Tokens (TypeScript)
 * Compatibilidade nominal com implementos agrícolas e plantadeiras mecânicas/pneumáticas.
 */

export const AgroThemeTokens = {
  name: 'AgroCalibre Industrial Theme',
  version: '2.0.0',
  compliance: {
    ipNotice: 'Marca independente. Nenhuma reprodução de logotipo, slogan ou propriedade industrial de terceiros.',
    nominalUseStandard: 'Compatibilidade informada exclusivamente como especificação técnica mecânica.',
  },
  colors: {
    primary: {
      DEFAULT: '#CE0B10', // Pantone 186C - Vermelho Institucional
      hover: '#B0090D',
      dark: '#8D1913',
      deep: '#460E0F',
      onPrimary: '#FFFFFF',
    },
    secondary: {
      DEFAULT: '#ECCA00', // Pantone 7405C - Amarelo Principal
      warm: '#E7B104',    // Pantone 124C - Amarelo Trigo
      onSecondary: '#373636',
    },
    surfaces: {
      darkSurface: '#373636',       // Cinza K 92%
      neutralDark: '#575756',       // Cinza K 80%
      neutralBorder: '#C6C6C6',     // Cinza K 30%
      backgroundLight: '#F8F9FA',   // Fundo Anti-ofuscamento
      pureWhite: '#FFFFFF',
    },
    status: {
      nominal: '#1E7E34',
      alert: '#ECCA00',
      critical: '#CE0B10',
    }
  },
  typography: {
    fontFamily: "'Montserrat', 'Myriad Pro', sans-serif",
    weights: {
      light: 300,
      regular: 400,
      semibold: 600,
      extrabold: 800,
      black: 900,
    },
    hierarchy: {
      displayKpi: {
        fontSize: '3.5rem', // 56px
        lineHeight: '1.05',
        fontWeight: 900, // Montserrat Black
        letterSpacing: '-0.02em',
      },
      headingLarge: {
        fontSize: '1.5rem', // 24px
        lineHeight: '1.25',
        fontWeight: 800, // Montserrat Extra Bold
      },
      sectionLabel: {
        fontSize: '1.125rem', // 18px
        lineHeight: '1.4',
        fontWeight: 600, // Montserrat Semi Bold
      },
      bodyText: {
        fontSize: '1rem', // 16px
        lineHeight: '1.5',
        fontWeight: 400, // Montserrat Regular
      },
      metadata: {
        fontSize: '0.8125rem', // 13px
        lineHeight: '1.4',
        fontWeight: 300, // Montserrat Light
      }
    }
  },
  ergonomics: {
    minTouchTargetDp: 48,
    standardTouchTargetDp: 56,
    sunlightContrastRatio: 'WCAG AA (mínimo 4.5:1 para texto regular, 7:1 para métricas)',
    cardRadiusPx: 16,
    buttonRadiusPx: 12,
  }
} as const;

export type AgroTheme = typeof AgroThemeTokens;

