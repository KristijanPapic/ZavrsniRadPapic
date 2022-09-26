using Carer.Common;
using Carer.Model.DomainModels;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Carer.Service.Common
{
    public interface IAdService
    {
        Task DeleteAdAsync(Guid id);
        Task<AdDomainModel> GetAdByIdAsync(Guid id,Guid userId);
        Task<List<AdDomainModel>> GetAllAdsAsync(AdFilter filter);
        Task PostAdAsync(AdDomainModel Ad);
        Task PutAdAsync(AdDomainModel Ad);
    }
}