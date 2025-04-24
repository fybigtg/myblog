import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
  {
    path: '',
    redirect: '/login'
  },{
    path: '/manage',
    name: 'Manage',
    redirect:'/manage/home',
    component: () => import(/* webpackChunkName: "Manage" */ '../views/MainView.vue'),
    children: [
      {path: 'home', name: 'Home', component: (()=>import('../components/Home.vue'))},
      {path: 'articleshow', name: 'ArticleShow', component: (()=>import('../components/ArticleShow.vue'))},
      {path: 'myinfo', name: 'MyInfo', component: (()=>import('../components/MyInfo.vue'))},
      {path: 'mymsg', name: 'MyMsg', component: (()=>import('../components/MyMsg.vue'))},
      {path: 'myarticle', name: 'MyArticle', component: (()=>import('../components/MyArticle.vue'))},
      {path: 'myeditor', name: 'MyEditor', component: (()=>import('../components/MyEditor.vue'))}

    ]
  },{
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },{
    path:'/login',
    name:'login',
    component: () => import(/* webpackChunkName: "login" */ '../views/LoginView.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
