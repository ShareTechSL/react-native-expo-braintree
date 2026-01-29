import { type ConfigPlugin, createRunOncePlugin } from '@expo/config-plugins';
import { withExpoBraintreeAndroid } from './withExpoBraintree.android';
import {
  withExpoBraintreePlist,
  withVenmoScheme,
  withExpoBraintreeAppDelegateAndWrapper,
} from './withExpoBraintree.ios';

const pkg = require('react-native-expo-braintree/package.json');

export type ExpoBraintreePluginProps = {
  /**
   * xCode project name, used for importing the swift expo braintree config header
   */
  xCodeProjectAppName?: string;

  /**
   * Android AppLink host
   */
  host: string;

  /**
   * Android AppLink pathPrefix
   */
  pathPrefix?: string;
};

export const withExpoBraintreePlugin: ConfigPlugin<ExpoBraintreePluginProps> = (
  expoConfig,
  props
) => {
  // Android mods
  let config = withExpoBraintreeAndroid(expoConfig, props);

  // IOS mods - AppDelegate and wrapper file use auto-detected language
  config = withExpoBraintreeAppDelegateAndWrapper(config, props);
  config = withExpoBraintreePlist(config);
  config = withVenmoScheme(config);

  return config;
};

export default createRunOncePlugin(
  withExpoBraintreePlugin,
  pkg.name,
  pkg.version
);
