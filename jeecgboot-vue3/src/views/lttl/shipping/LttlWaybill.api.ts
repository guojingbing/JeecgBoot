import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/lttl/shipping/po/list',
  save = '/lttl/shipping/po/add',
  edit = '/lttl/shipping/po/edit',
  deleteOne = '/lttl/shipping/po/delete',
  deleteBatch = '/lttl/shipping/po/deleteBatch',
  importExcel = '/lttl/shipping/po/importExcel',
  exportXls = '/lttl/shipping/po/exportXls',
  lttlWaybillGoodsList = '/lttl/shipping/po/goods',
  lttlWaybillFeeList = '/lttl/shipping/po/fees',
}
/**
 * 导出api
 * @param params
 */
export const getExportUrl = Api.exportXls;

/**
 * 导入api
 */
export const getImportUrl = Api.importExcel;
/**
 * 子表单查询接口
 * @param params
 */
export const queryLttlWaybillGoods = Api.lttlWaybillGoodsList
/**
 * 子表单查询接口
 * @param params
 */
export const queryLttlWaybillFee = Api.lttlWaybillFeeList
/**
 * 列表接口
 * @param params
 */
export const list = (params) =>
  defHttp.get({url: Api.list, params});

/**
 * 删除单个
 */
export const deleteOne = (params,handleSuccess) => {
  return defHttp.delete({url: Api.deleteOne, params}, {joinParamsToUrl: true}).then(() => {
    handleSuccess();
  });
}
/**
 * 批量删除
 * @param params
 */
export const batchDelete = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({url: Api.deleteBatch, data: params}, {joinParamsToUrl: true}).then(() => {
        handleSuccess();
      });
    }
  });
}
/**
 * 保存或者更新
 * @param params
 */
export const saveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.edit : Api.save;
  return defHttp.post({url: url, params});
}
/**
 * 子表列表接口
 * @param params
 */
export const lttlWaybillGoodsList = (params) =>
  defHttp.get({url: Api.lttlWaybillGoodsList, params},{isTransformResponse:false});
/**
 * 子表列表接口
 * @param params
 */
export const lttlWaybillFeeList = (params) =>
  defHttp.get({url: Api.lttlWaybillFeeList, params},{isTransformResponse:false});
