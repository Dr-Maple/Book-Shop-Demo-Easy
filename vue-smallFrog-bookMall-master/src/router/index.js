/* eslint-disable */
import Vue from 'vue'
import Router from 'vue-router'

import navbar from '@/components/frame/navbar'
import login from '@/components/login/login'
import register from '@/components/login/register'
import setting from '@/components/main/setting'
import home from '@/components/main/home'
import category from '@/components/main/category'
import bookInfo from '@/components/main/bookInfo'
import newProduct from '@/components/main/newProduct'
import onSale from '@/components/main/onSale'
import cart from '@/components/main/cart'
import settle from '@/components/main/settle'
import pay from '@/components/main/pay'
import order from '@/components/main/order'
import permission from '@/components/main/403'
import notFound from '@/components/main/404'
import adminHome from '@/components/main/adminHome'
import personalCenter from '@/components/main/personalCenter'
import editAddress from '@/components/main/editAddress'
import adminBooks from '@/components/main/adminBooks'
import adminOrders from '@/components/main/adminOrders'
import adminInfo from '@/components/main/adminInfo'
import updatePassword from '@/components/main/updatePassword'
import addAdmin from '@/components/main/addAdmin'


Vue.use(Router)

const router = new Router({
    routes: [{
            path: '/',
            redirect: '/home'
        },
        {
            path:'/adminHome',
            name: "adminHome",
            component: adminHome,
            redirect: '/adminHome/adminBooks',
            meta:{
                title: "管理员"
            },
            children:[{
                    path: 'adminBooks',
                    name: 'adminBooks',
                    component: adminBooks,
                    meta: {
                        title: '图书管理'
                    }
                },{
                    path: 'adminOrders',
                    name: 'adminOrders',
                    component: adminOrders,
                    meta: {
                        title: '租借管理'
                    }
                },{
                    path:'adminInfo',
                    name: "adminInfo",
                    component: adminInfo,
                    meta:{
                        title: "修改个人信息"
                    },
                },
                {
                    path:'addAdmin',
                    name: "addAdmin",
                    component: addAdmin,
                    meta:{
                        title: "新增管理员"
                    },
                }
            ]
        },
        {
            path: '/',
            name: 'navbar',
            component: navbar,
            children: [{
                    path: '/login',
                    name: 'login',
                    component: login,
                    meta: {
                        title: '登录'
                    }
                },
                {
                    path: '/register',
                    name: 'register',
                    component: register,
                    meta: {
                        title: '账号注册'
                    }
                },
                {
                    path: '/home',
                    name: 'home',
                    component: home,
                    meta: {
                        title: '首页'
                    }
                },
                {
                    path: '/category',
                    name: 'category',
                    component: category,
                    meta: {
                        keepAlive: true,
                        title: '书籍分类'
                    }
                    
                },
                {
                    path: '/onSale',
                    name: 'onSale',
                    component: onSale,
                    meta: {
                        title: ''//特价市场
                    }
                },
                {
                    path: '/bookInfo',
                    name: 'bookInfo',
                    component: bookInfo,
                    meta: {
                        title: '书籍详情'
                    }
                },
                {
                    path: '/newProduct',
                    name: 'newProduct',
                    component: newProduct,
                    meta: {
                        title: '新货上架'
                    }
                },
                {
                    path: '/shopping/cart',
                    name: 'cart',
                    component: cart,
                    meta: {
                        title: '租借管理',//购物车
                    }
                },
                {
                    path: '/shopping/settle',
                    name: 'settle',
                    component: settle,
                    meta: {
                        title: '确认订单',
                    }
                },
                {
                    path: '/order',
                    name: 'order',
                    component: order,
                    meta: {
                        title: '我的订单',
                    }
                },
                {
                    path: '/setting',
                    name: 'setting',
                    component: setting,
                    redirect: '/setting/personalCenter',
                    children:[{
                            path: 'personalCenter',
                            name: 'personalCenter',
                            component: personalCenter,
                            meta: {
                                title: "个人信息维护"
                            }
                        },{
                            path: 'editAddress',
                            name: 'editAddress',
                            component: editAddress,
                            meta: {
                                title: "收货地址"
                            }
                        },{
                            path: 'updatePassword',
                            name: 'updatePassword',
                            component: updatePassword,
                            meta: {
                                title: "修改密码"
                            }
                        }
                ],
                    meta: {
                        title: '个人中心',
                        requiresAuth: true
                    }
                },
                {
                    path: '/403',
                    name: 'permission',
                    component: permission,
                    meta: {
                        title: '403',
                    }
                },
                {
                    path: '/404',
                    name: 'notFound',
                    component: notFound,
                    meta: {
                        title: '404',
                    }
                },
                {
                    path: "*", // 此处需特别注意置于最底部
                    redirect: "/404"
                }
            ]
        }
    ]
})


export default router