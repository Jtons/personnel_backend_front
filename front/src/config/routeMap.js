import Loadable from 'react-loadable';
import Loading from '@/components/Loading'
const Error404 = Loadable({loader: () => import(/*webpackChunkName:'Error404'*/'@/views/error/404'),loading: Loading});
const User = Loadable({loader: () => import(/*webpackChunkName:'User'*/'@/views/user'),loading: Loading});

export default [
  { path: "/error/404", component: Error404 },
  { path: "/user", component: User, roles: ["admin", "pm", "common"] },
];
