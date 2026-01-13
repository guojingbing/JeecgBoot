import { defHttp } from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/lttl/base/station/list',
  save = '/lttl/base/station/add',
  edit = '/lttl/base/station/edit',
  deleteOne = '/lttl/base/station/delete',
  deleteBatch = '/lttl/base/station/deleteBatch',
  importExcel = '/lttl/base/station/importExcel',
  exportXls = '/lttl/base/station/exportXls',
  info = '/lttl/base/station/{id}',
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
 * 根据stationId获取站点信息
 * @param params 包含stationId的参数对象
 * @returns 包含stationId的参数对象 
 */
export const getStationByStationId = (params) =>
  defHttp.get({url: Api.info.replace('{id}', params.id), params});

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
