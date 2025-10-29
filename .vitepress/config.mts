import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  srcDir: "./src",
  
  title: "KPI Forms",
  description: "A VitePress Site",
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Home', link: '/' },
      { text: 'Project', link: '/project' }
    ],

    sidebar: [
      {
        text: 'Pages',
        items: [
          { text: 'Вступ', link: '/intro' },
		  { text: 'Аналіз предметної області', link: '/docs/requirements/state-of-the-art' },
		  { text: 'Призначення розроблюваної системи', link: '/appointment' },
		  { text: 'Аналіз зацікавлених осіб для системи організації та управління опитуваннями експертів', link: '/docs/requirements/stakeholders-needs' },
		  { text: 'Розробка загальної діаграми прецедентів', link: '/prētsedentiv'},
		  { text: 'Реляційна схема', link: '/relational_schema'},  
		  { text: 'UseCase', link: '/useCase'},
		  { text: 'Бізнес модель та діаграма системи опитування', link: '/business_models'},
		  { text: 'Реалізація інформаційного та програмного забезпечення', link: '/SQL' },
		  { text: 'Реалізація об’єктно-реляційного відображення', link: '/implementation' }, 
		  { text: 'Тестування працездатності системи', link: '/Test' },
		  { text: 'Висновки', link: '/vusnovki' },
		  { text: 'Автори', link: '/autors' },
        ]
      }
    ]
  }
})
