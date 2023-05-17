/**
 * 通用js方法封装处理
 * Copyright (c) 2019 ruoyi
 */
// 字符串格式化(%s )
export function sprintf(str: string) {
  // eslint-disable-next-line prefer-rest-params
  const args = arguments;
  let flag = true,
      i = 1;
  str = str.replace(/%s/g, function () {
      const arg = args[i++];
      if (typeof arg === 'undefined') {
          flag = false;
          return '';
      }
      return arg;
  });
  return flag ? str : '';
}

// 转换字符串，undefined,null等转化为""
export function parseEmpty(str: string) {
  if (!str || str === 'undefined' || str === 'null') {
      return '';
  }
  return str;
}

// // 数据合并
// export function mergeRecursive(source, target) {
//     for (var p in target) {
//         try {
//             if (target[p].constructor == Object) {
//                 source[p] = mergeRecursive(source[p], target[p]);
//             } else {
//                 source[p] = target[p];
//             }
//         } catch (e) {
//             source[p] = target[p];
//         }
//     }
//     return source;
// }

/**
* 构造树型结构数据
* @param {*} data 数据源
* @param {*} id id字段 默认 'id'
* @param {*} parentId 父节点字段 默认 'parentId'
* @param {*} children 孩子节点字段 默认 'children'
*/
export function handleTree(data: any, id: any, parentId?: any, children?: any) {
  const config = {
      id: id || 'id',
      parentId: parentId || 'parentId',
      childrenList: children || 'children',
  };

  const childrenListMap: any = {};
  const nodeIds: any = {};
  const tree = [];

  for (const d of data) {
      const parentId = d[config.parentId];
      if (childrenListMap[parentId] == null) {
          childrenListMap[parentId] = [];
      }
      nodeIds[d[config.id]] = d;
      childrenListMap[parentId].push(d);
  }

  for (const d of data) {
      const parentId = d[config.parentId];
      if (nodeIds[parentId] == null) {
          tree.push(d);
      }
  }

  for (const t of tree) {
      adaptToChildrenList(t);
  }

  function adaptToChildrenList(o: any) {
      if (childrenListMap[o[config.id]] !== null) {
          o[config.childrenList] = childrenListMap[o[config.id]];
      }
      if (o[config.childrenList]) {
          for (const c of o[config.childrenList]) {
              adaptToChildrenList(c);
          }
      }
  }
  return tree;
}

/**
* 参数处理
* @param {*} params  参数
*/
export function tansParams(params: any) {
  let result = '';
  for (const propName of Object.keys(params)) {
      const value = params[propName];
      const part = encodeURIComponent(propName) + '=';
      if (value !== null && value !== '' && typeof value !== 'undefined') {
          if (typeof value === 'object') {
              for (const key of Object.keys(value)) {
                  if (value[key] !== null && value[key] !== '' && typeof value[key] !== 'undefined') {
                      const params = propName + '[' + key + ']';
                      const subPart = encodeURIComponent(params) + '=';
                      result += subPart + encodeURIComponent(value[key]) + '&';
                  }
              }
          } else {
              result += part + encodeURIComponent(value) + '&';
          }
      }
  }
  return result;
}

// 返回项目路径
export function getNormalPath(p: any) {
  if (p.length === 0 || !p || p === 'undefined') {
      return p;
  }
  const res = p.replace('//', '/');
  if (res[res.length - 1] === '/') {
      return res.slice(0, res.length - 1);
  }
  return res;
}

// 验证是否为blob格式
export function blobValidate(data: any) {
  return data.type !== 'application/json';
}
