import * as types from "../action-types";
import { getToken } from "@/utils/auth";
const initUserInfo = {
  name: "",
  role: "",
  avatar:"",
  token: getToken(),
};
export default function user(state = initUserInfo, action) {
  console.log('state=',state);
  console.log('action=',action);
  switch (action.type) {
    case types.USER_SET_USER_TOKEN:
      console.log('1');
      return {
        ...state,
        token: action.token
      };
    case types.USER_SET_USER_INFO:
      console.log('2');
      console.log('action.name=',action.name);
      return {
        ...state,
        name: action.name,
        role: action.role,
        avatar: action.role,
      };
    case types.USER_RESET_USER:
      console.log('3');
      return {};
    default:
      console.log('4');
      return state;
  }
}
