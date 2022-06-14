// Copyright (c) 2021 The Brave Authors. All rights reserved.
// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this file,
// you can obtain one at http://mozilla.org/MPL/2.0/.

#ifndef BRAVE_BROWSER_EXTENSIONS_API_FTX_API_H_
#define BRAVE_BROWSER_EXTENSIONS_API_FTX_API_H_

#include <string>
#include <vector>

#include "brave/components/ftx/browser/ftx_service.h"
#include "extensions/browser/extension_function.h"

class Profile;

namespace extensions {
namespace api {

class FtxGetFuturesDataFunction : public ExtensionFunction {
 public:
  DECLARE_EXTENSION_FUNCTION("ftx.getFuturesData", UNKNOWN)

 protected:
  ~FtxGetFuturesDataFunction() override {}
  void OnFuturesData(const FTXFuturesData& data);

  ResponseAction Run() override;
};

class FtxGetChartDataFunction : public ExtensionFunction {
 public:
  DECLARE_EXTENSION_FUNCTION("ftx.getChartData", UNKNOWN)

 protected:
  ~FtxGetChartDataFunction() override {}
  void OnChartData(const FTXChartData& data);

  ResponseAction Run() override;
};

class FtxSetOauthHostFunction : public ExtensionFunction {
 public:
  DECLARE_EXTENSION_FUNCTION("ftx.setOauthHost", UNKNOWN)

 protected:
  ~FtxSetOauthHostFunction() override {}

  ResponseAction Run() override;
};

class FtxGetOauthHostFunction : public ExtensionFunction {
 public:
  DECLARE_EXTENSION_FUNCTION("ftx.getOauthHost", UNKNOWN)

 protected:
  ~FtxGetOauthHostFunction() override {}
  void OnOauthHost(const std::string& host);

  ResponseAction Run() override;
};

class FtxGetClientUrlFunction : public ExtensionFunction {
 public:
  DECLARE_EXTENSION_FUNCTION("ftx.getClientUrl", UNKNOWN)

 protected:
  ~FtxGetClientUrlFunction() override {}
  ResponseAction Run() override;
};

class FtxDisconnectFunction : public ExtensionFunction {
 public:
  DECLARE_EXTENSION_FUNCTION("ftx.disconnect", UNKNOWN)

 protected:
  ~FtxDisconnectFunction() override {}
  ResponseAction Run() override;
};

class FtxGetAccountBalancesFunction : public ExtensionFunction {
 public:
  DECLARE_EXTENSION_FUNCTION("ftx.getAccountBalances", UNKNOWN)

 protected:
  ~FtxGetAccountBalancesFunction() override {}
  void OnGetAccountBalances(const FTXAccountBalances& balances,
                            bool auth_invalid);

  ResponseAction Run() override;
};

class FtxIsSupportedFunction : public ExtensionFunction {
 public:
  DECLARE_EXTENSION_FUNCTION("ftx.isSupported", UNKNOWN)

 protected:
  ~FtxIsSupportedFunction() override {}
  ResponseAction Run() override;
};

class FtxGetConvertQuoteFunction : public ExtensionFunction {
 public:
  DECLARE_EXTENSION_FUNCTION("ftx.getConvertQuote", UNKNOWN)

 protected:
  ~FtxGetConvertQuoteFunction() override {}
  void OnConvertQuote(const std::string& quote_id);

  ResponseAction Run() override;
};

class FtxGetConvertQuoteInfoFunction : public ExtensionFunction {
 public:
  DECLARE_EXTENSION_FUNCTION("ftx.getConvertQuoteInfo", UNKNOWN)

 protected:
  ~FtxGetConvertQuoteInfoFunction() override {}
  void OnConvertQuoteInfo(const std::string& cost,
                          const std::string& price,
                          const std::string& proceeds);

  ResponseAction Run() override;
};

class FtxExecuteConvertQuoteFunction : public ExtensionFunction {
 public:
  DECLARE_EXTENSION_FUNCTION("ftx.executeConvertQuote", UNKNOWN)

 protected:
  ~FtxExecuteConvertQuoteFunction() override {}
  void OnExecuteConvertQuote(bool success);

  ResponseAction Run() override;
};

}  // namespace api
}  // namespace extensions

#endif  // BRAVE_BROWSER_EXTENSIONS_API_FTX_API_H_
