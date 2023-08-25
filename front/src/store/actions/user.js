import * as types from "../action-types";
import { reqUserInfo } from "@/api/user";

export const getUserInfo = ()=>(dispatch) => {
  return new Promise((resolve, reject) => {
    reqUserInfo()
      .then((response) => {
        const { data } = response;
        if (response.code == 200) {
          const userInfo = data;
           localStorage.setItem('avatar',userInfo.avatar)
           localStorage.setItem('name',userInfo.name)
          dispatch(setUserInfo(userInfo));
          resolve(data);
        } else {
          const msg = data.msg;
          reject(msg);
        }
      })
      .catch((error) => {
        reject(error);
      });
  });
};

export const setUserToken = (token) => {
  return {
    type: types.USER_SET_USER_TOKEN,
    token,
  };
};

export const setUserInfo = (userInfo) => {
  return {
    ...userInfo,
    type: types.USER_SET_USER_INFO,
    
  };
};

export const resetUser = () => {
  return {
    type: types.USER_RESET_USER,
  };
};
