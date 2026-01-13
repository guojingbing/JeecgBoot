import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '网点代码',
    align:"center",
    dataIndex: 'stationCode'
   },
   {
    title: '网点名称',
    align:"center",
    dataIndex: 'stationName'
   },
   {
    title: '地区',
    align:"center",
    dataIndex: 'areaCode',
   },
   {
    title: '详细地址',
    align:"center",
    dataIndex: 'address'
   },
   {
    title: '经纬度',
    align:"center",
    dataIndex: 'latLng'
   },
   {
    title: '联系人',
    align:"center",
    dataIndex: 'contactName'
   },
   {
    title: '联系电话',
    align:"center",
    dataIndex: 'contactTel'
   },
   {
    title: '默认到站',
    align:"center",
    dataIndex: 'defaultDestStation_dictText'
   },
   {
    title: '默认中转站',
    align:"center",
    dataIndex: 'defaultTransferStation_dictText'
   },
   {
    title: '备注',
    align:"center",
    dataIndex: 'remark'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '网点代码',
    field: 'stationCode',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入网点代码!'},
          ];
     },
  },
  {
    label: '网点名称',
    field: 'stationName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入网点名称!'},
          ];
     },
  },
  {
    label: '地区',
    field: 'areaCode',
    component: 'JAreaLinkage',
    componentProps: {
      saveCode: 'region',
    },
  },
  {
    label: '详细地址',
    field: 'address',
    component: 'Input',
  },
  {
    label: '经纬度',
    field: 'latLng',
    component: 'Input',
  },
  {
    label: '联系人',
    field: 'contactName',
    component: 'Input',
  },
  {
    label: '联系电话',
    field: 'contactTel',
    component: 'Input',
  },
  {
    label: '默认到站',
    field: 'defaultDestStation',
    component: 'JSearchSelect',
    componentProps:{
       dict:"lttl_base_station,station_name,id"
    },
  },
  {
    label: '默认中转站',
    field: 'defaultTransferStation',
    component: 'JSearchSelect',
    componentProps:{
       dict:"lttl_base_station,station_name,id"
    },
  },
  {
    label: '备注',
    field: 'remark',
    component: 'Input',
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  stationCode: {title: '网点代码',order: 0,view: 'text', type: 'string',},
  stationName: {title: '网点名称',order: 1,view: 'text', type: 'string',},
  areaCode: {title: '地区',order: 2,view: 'pca', type: 'string',},
  address: {title: '详细地址',order: 3,view: 'text', type: 'string',},
  latLng: {title: '经纬度',order: 4,view: 'text', type: 'string',},
  contactName: {title: '联系人',order: 5,view: 'text', type: 'string',},
  contactTel: {title: '联系电话',order: 6,view: 'text', type: 'string',},
  defaultDestStation: {title: '默认到站',order: 7,view: 'sel_search', type: 'string',dictTable: "lttl_base_station", dictCode: 'id', dictText: 'station_name',},
  defaultTransferStation: {title: '默认中转站',order: 8,view: 'sel_search', type: 'string',dictTable: "lttl_base_station", dictCode: 'id', dictText: 'station_name',},
  remark: {title: '备注',order: 9,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}